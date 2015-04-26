from subprocess import call
import sys

__author__ = 'charlie'


def main(tag_name):
    switch_to_master()
    tag_release(tag_name)
    push_tag(tag_name)


def switch_to_master():
    print call(['git', 'checkout', 'master'])


def tag_release(tag_name):
    def commit_message():
        return "Tagging RELEASE %s. Consult release notes for more details." % tag_name
    print call(['git', 'tag', '-a', tag_name, '-m', commit_message()])


def push_tag(tag_name):
    print call(['git', 'push', 'origin', tag_name])

if __name__ == "__main__":
    main(sys.argv[1])